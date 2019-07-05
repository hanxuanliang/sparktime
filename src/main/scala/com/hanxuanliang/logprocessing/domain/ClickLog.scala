package com.hanxuanliang.logprocessing.domain

case class ClickLog(
    ip:String,
    time: String,
    courseId: Int,
    statusCode:Int,
    referer:String
)
