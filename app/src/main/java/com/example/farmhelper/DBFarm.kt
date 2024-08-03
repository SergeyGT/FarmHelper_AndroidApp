package com.example.farmhelper

import User
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.lifecycle.ViewModelProvider

class DBFarm(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "user", factory, 8){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (idUsers INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, pass TEXT)"
        db!!.execSQL(query)
        println("Created users table")
        db!!.execSQL(
            """
            CREATE TABLE employee (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name TEXT ,
                profession TEXT 
            )
            """
        )
        println("Created employee table")
        db!!.execSQL(
            """
            CREATE TABLE salary (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employee_id INT ,
                work_date TEXT ,
                work_type TEXT ,
                hours REAL ,
                hourly_rate INT ,
                hectares REAL ,
                hectare_rate INT ,
                FOREIGN KEY (employee_id) REFERENCES Employee(id)
            )
            """
        )
        println("create salary")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users ")
        db!!.execSQL("DROP TABLE IF EXISTS salary")
        db!!.execSQL("DROP TABLE IF EXISTS employee")
        onCreate(db)
        println("DB upgrade")
    }
    fun dropAllTables() {
        val db = this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS users ")
        db.execSQL("DROP TABLE IF EXISTS employee")
        db.execSQL("DROP TABLE IF EXISTS salary")
        db.close()
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }

    fun getUser(login: String, pass: String) : Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null )

        return result.moveToFirst()
    }

    fun addEmployee(employee: employee) {
        val valuesEmployee = ContentValues()
        valuesEmployee.put("full_name", employee.fio)
        valuesEmployee.put("profession", employee.proffesion)

        val db = this.writableDatabase
        db.insert("employee", null, valuesEmployee)
        db.close()
    }

    fun addSalary(salary: Salary) {
        val valuesSalary = ContentValues()
        valuesSalary.put("employee_id", salary.employeeId)
        valuesSalary.put("work_date", salary.workDate)
        valuesSalary.put("work_type", salary.workType)
        valuesSalary.put("hours", salary.hours)
        valuesSalary.put("hourly_rate", salary.hoursRate)
        valuesSalary.put("hectares", salary.hectares)
        valuesSalary.put("hectare_rate", salary.hectaresRate)

        val db = this.writableDatabase
        db.insert("salary", null, valuesSalary)
        db.close()

    }

    fun getAllEmployees(): List<employee> {
        val db = this.readableDatabase
        val cursor = db.query("employee", arrayOf("id", "full_name", "profession"), null, null, null, null, null)
        val employees = mutableListOf<employee>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
            val profession = cursor.getString(cursor.getColumnIndexOrThrow("profession"))
            employees.add(employee(id, fullName, profession))
        }

        println("Employees fetched: ${employees.size}")
        return employees

    }

    fun calculateSalary(employeeId: Int, startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        Log.d("DBFarm", "Calculating salary for employeeId: $employeeId, from: $startDate to: $endDate")
        val query = """
            SELECT SUM(hours * hourly_rate + hectares * hectare_rate) 
            FROM salary 
            WHERE employee_id = ? 
            AND work_date BETWEEN ? AND ?
        """
        // Логирование запроса и параметров
        Log.d("DBFarm", "SQL Query: $query")
        Log.d("DBFarm", "Parameters: ${arrayOf(employeeId.toString(), startDate, endDate).joinToString(", ")}")
        val cursor: Cursor = db.rawQuery(query, arrayOf(employeeId.toString(), startDate, endDate))
        val totalSalary = if (cursor.moveToFirst()) {
            val result = cursor.getDouble(0)
            Log.d("DBFarm", "Total Salary: $result")
            result
        } else {
            Log.d("DBFarm", "No data found.")
            0.0
        }
        cursor.close()
        db.close()
        return totalSalary
    }



}