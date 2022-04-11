package com.husaynhakeem.activityembeddingsample.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    val letters: LiveData<List<String>> = MutableLiveData(allUppercaseLetters())

    private fun allUppercaseLetters(): List<String> {
        return ('A'..'Z').map { it.toString() }
    }
}