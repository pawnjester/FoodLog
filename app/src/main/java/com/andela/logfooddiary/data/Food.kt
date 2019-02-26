package com.andela.logfooddiary.data


data class Food (
        var id: String? = "",
        var breakfast: String? ="",
        var lunch: String? = "",
        var dinner: String? = "",
        var mood: String? = "",
        var date: String? = "",
        var user: String? = ""
){
    constructor() : this("", "", "","", "", "", "")

}