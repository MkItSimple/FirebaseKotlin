package com.example.firebasekotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasekotlin.data.Author
import com.example.firebasekotlin.data.NODE_AUTHORS
import com.google.firebase.database.FirebaseDatabase


class AuthorsViewModel : ViewModel() {

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>>
        get() = _authors

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author>
        get() = _author

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addAuthor(author: Author) {
        author.id = dbAuthors.push().key
        dbAuthors.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }
}