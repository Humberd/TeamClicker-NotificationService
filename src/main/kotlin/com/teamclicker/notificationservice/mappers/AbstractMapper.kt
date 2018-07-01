package com.teamclicker.notificationservice.mappers

abstract class AbstractMapper<FROM, TO> {
    abstract fun parse(from: FROM): TO
}