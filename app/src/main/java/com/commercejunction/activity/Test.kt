package com.commercejunction.activity

internal class Test {
    var url = "https://www.youtube.com/watch?v=tResEeK6P5I"
    var videoId =
        url.split("v=".toRegex()).toTypedArray()[1] //you will get this video id "tResEeK6P5I"
    var thumbnailMq =
        "http://img.youtube.com/vi/$videoId/mqdefault.jpg" //medium quality thumbnail
    var thumbnailHq =
        "http://img.youtube.com/vi/$videoId/hqdefault.jpg" //high quality thumbnail
    //your final urls will be like
    //img.youtube.com/vi/tResEeK6P5I/hqdefault.jpg
}