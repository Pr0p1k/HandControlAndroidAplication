package com.handcontrol.api

import com.handcontrol.model.Action
import com.handcontrol.model.Gesture

class BluetoothHandler : IApiHandler {
    override suspend fun getGestures(): MutableList<Gesture> {
        TODO("Not yet implemented")
    }

    override suspend fun saveGesture(gesture: Gesture) {
        TODO("Not yet implemented")
    }

    override suspend fun performGesture(gesture: Gesture) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGesture(gestureId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setPositions(action: Action) {
        TODO("Not yet implemented")
    }
}