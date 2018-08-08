package com.genius.amateur.whatschat.models

class Users() {
    var display_name: String? = null
    var image: String? = null
    var status: String? = null
    var thumb_image: String? = null

    constructor(display_name: String?, image: String?, status: String?, thumb_image: String?) : this() {
        this.display_name = display_name
        this.image = image
        this.status = status
        this.thumb_image = thumb_image
    }
}