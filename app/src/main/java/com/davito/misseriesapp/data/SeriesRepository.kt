package com.davito.misseriesapp.data

import android.util.Log
import com.davito.misseriesapp.model.Serie
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SeriesRepository {

    private var db = Firebase.firestore
    private var auth : FirebaseAuth = Firebase.auth


    suspend fun saveSerie(serie: Serie): ResourceRemote<String?> {
        return try {
            val uid = auth.currentUser?.uid
            val path = uid?.let { db.collection("users").document(it).collection("series")}
            val documentoSerie = path?.document()
            serie.id = documentoSerie?.id
            serie.id?.let { path?.document(it)?.set(serie)?.await() }
            ResourceRemote.Success(data = uid)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesFirestoreEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesNetworkEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadSeries():  ResourceRemote<QuerySnapshot?> {
        return try {
            val doref= auth.uid?.let { db.collection("users").document(it).collection("series")}
            val result = doref?.get()?.await()
            ResourceRemote.Success(data = result)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }
}