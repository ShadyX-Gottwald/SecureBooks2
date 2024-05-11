package com.example.securebooks2.Activities.Utilities

class DateUtils {

    companion object{
        fun validDateMatch(dateString: String): Boolean {
            return dateString
                .matches(
                    Regex("(\\b(0?[1-9]|[12]\\d|30|31)[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](\\d{4}|\\d{2})\\b)|(\\b(0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](0?[1-9]|[12]\\d|30|31)[^\\w\\d\\r\\n:](\\d{4}|\\d{2})\\b)"))
        }

    }

}