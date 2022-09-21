package com.husaynhakeem.barberapp.data

import com.husaynhakeem.barberapp.screen.home.Barber

val barbers: List<Barber> by lazy {
    (0..10).map {
        Barber(
            it.toLong(),
            "https://randomuser.me/api/portraits/men/${it}.jpg",
        )
    }
}