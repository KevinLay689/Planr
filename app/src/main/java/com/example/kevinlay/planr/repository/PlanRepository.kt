package com.example.kevinlay.planr.repository

import com.example.kevinlay.planr.repository.local.LocalDbSource
import com.example.kevinlay.planr.repository.remote.RemoteDbSource

class PlanRepository(private val remoteDbSource: RemoteDbSource,
                     private val localDbSource: LocalDbSource) {

}