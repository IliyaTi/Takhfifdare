package com.example.takhfifdar.util

class NumberUnicodeAdapter {

    val arabicChars = arrayOf('۰','۱','۲','۳','۴','۵','۶','۷','۸','۹')
    val res = StringBuilder()

    fun convert(a: String): String {
        for (ch in a) {
            when (ch) {
                '0' -> res.append(arabicChars[0])
                '1' -> res.append(arabicChars[1])
                '2' -> res.append(arabicChars[2])
                '3' -> res.append(arabicChars[3])
                '4' -> res.append(arabicChars[4])
                '5' -> res.append(arabicChars[5])
                '6' -> res.append(arabicChars[6])
                '7' -> res.append(arabicChars[7])
                '8' -> res.append(arabicChars[8])
                '9' -> res.append(arabicChars[9])
                else -> res.append(ch)
            }
        }
        return res.toString()
    }

    fun format(a: Int): String {
        val string = a.toString()
        var res = StringBuilder()
        val reverse = string.reversed()
        for (i in 1 .. reverse.length) {
            res.append(reverse[i - 1])
            if (i % 3 == 0) res.append(",")
        }
        res = res.reverse()
        if (res[0] == ',') res = res.deleteCharAt(0)
        return res.toString()
    }

}