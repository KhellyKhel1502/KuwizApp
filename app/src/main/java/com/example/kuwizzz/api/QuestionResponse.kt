package com.example.kuwizzz.api

import android.os.Parcel
import android.os.Parcelable

// Define QuestionResponse class
data class QuestionResponse(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringList(options)
        parcel.writeString(correctAnswer)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<QuestionResponse> {
        override fun createFromParcel(parcel: Parcel): QuestionResponse {
            return QuestionResponse(parcel)
        }

        override fun newArray(size: Int): Array<QuestionResponse?> {
            return arrayOfNulls(size)
        }
    }
}
