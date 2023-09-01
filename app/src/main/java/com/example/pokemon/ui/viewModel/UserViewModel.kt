package com.example.pokemon.ui.viewModel

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.R
import com.example.pokemon.data.user.User
import com.example.pokemon.data.user.UserRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    var userUiState by mutableStateOf(UserUiState())
        private set


    init {
        viewModelScope.launch {
            if (userRepository.isEmpty()) {
                userRepository.insert(UserUiState().toUser())
            }
            userUiState = userRepository.getUser().filterNotNull().first().toUserUiState()
        }
    }

    suspend fun lvlUp() {
        var user = userUiState.toUser()
        user = user.copy(lvl = user.lvl + 1)
        userRepository.update(user)
        updateUiState(user)
    }

    suspend fun rename(newName: String) {
        var user = userUiState.toUser()
        user = user.copy(name = newName)
        userRepository.update(user)
        updateUiState(user)
    }

    suspend fun changePierresQuantity(quantity: Int) {
        var user = userUiState.toUser()
        val newQuantity = user.pierres + quantity
        user = user.copy(pierres = if(newQuantity < 0) 0 else newQuantity)
        userRepository.update(user)
        updateUiState(user)
    }

    suspend fun changeUserPhoto(@DrawableRes photoId: Int) {
        var user = userUiState.toUser()
        user = user.copy(photo = photoId)
        userRepository.update(user)
        updateUiState(user)
    }

    fun updateUiState(user: User) {
        userUiState = user.toUserUiState()
    }
}

data class UserUiState(
    val id: Int = 1,
    val name: String = "",
    val lvl: String = "1",
    val pierres: String = "",
    @DrawableRes val photo: Int = R.drawable.red
)

fun User.toUserUiState(): UserUiState = UserUiState(
    id = id,
    name = name,
    lvl = lvl.toString(),
    pierres = pierres.toString(),
    photo = photo
)

fun UserUiState.toUser(): User = User(
    id = id,
    name = name,
    lvl = lvl.toIntOrNull() ?: 1,
    pierres = pierres.toIntOrNull() ?: 0,
    photo = photo
)