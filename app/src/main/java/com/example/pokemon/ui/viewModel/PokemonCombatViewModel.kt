package com.example.pokemon.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.data.pokemon.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val TIME = 500L

class PokemonCombatViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CombatUiState())
    val uiState: StateFlow<CombatUiState> = _uiState.asStateFlow()

    fun setAlly(ally: Pokemon?) {
        if (ally != null) {
            _uiState.update {
                it.copy(
                    ally = ally,
                    allyHp = ally.hp
                )
            }
        }
    }

    fun setEnnemy(ennemy: Pokemon?) {
        if (ennemy != null) {
            _uiState.update {
                it.copy(
                    ennemy = ennemy,
                    ennemyHp = ennemy.hp
                )
            }
        }
    }

    fun ennemyProgressFactor(): Float {
        if (_uiState.value.ennemy != null) {
            return _uiState.value.ennemyHp / _uiState.value.ennemy!!.hp.toFloat()
        }
        return 0f
    }

    fun allyProgressFactor(): Float {
        if (_uiState.value.ally != null) {
            return _uiState.value.allyHp / _uiState.value.ally!!.hp.toFloat()
        }
        return 0f
    }

    fun ennemyAttack() {
        val damage = (_uiState.value.ennemy!!.att - _uiState.value.ally!!.def)
        _uiState.update {
            it.copy(allyHp = _uiState.value.allyHp - (if (damage < 0) 1 else damage))
        }
    }

    fun allyAttack() {
        val damage = (_uiState.value.ally!!.att - _uiState.value.ennemy!!.def)
        _uiState.update {
            it.copy(ennemyHp = _uiState.value.ennemyHp - (if (damage < 0) 1 else damage))
        }
    }

    suspend fun combat() {
        Log.i("combat", "ally : ${_uiState.value.ally!!.hp}")
        Log.i("combat", "ennemy : ${_uiState.value.ennemy!!.hp}")
        if (_uiState.value.ally != null && _uiState.value.ennemy != null) {
            Log.i("combat", "ally : ${_uiState.value.ally!!.hp}")
            Log.i("combat", "ennemy : ${_uiState.value.ennemy!!.hp}")

            _uiState.update {
                it.copy(isFighting = true, isFinished = false)
            }

            while (_uiState.value.allyHp > 0 && _uiState.value.ennemyHp > 0) {
                if(_uiState.value.ennemy!!.speed > _uiState.value.ally!!.speed) {
                    ennemyAttack()
                    delay(TIME)
                    if (_uiState.value.allyHp >= 0) {
                        allyAttack()
                    }
                } else {
                    allyAttack()
                    delay(TIME)
                    if (_uiState.value.ennemyHp >= 0) {
                        ennemyAttack()
                    }
                }
            }
            _uiState.update {
                it.copy(isFighting = false)
            }
            if (_uiState.value.ennemyHp <= 0) {
                _uiState.update {
                    it.copy(isFinished = true)
                }
            }
        }

    }

    fun isWin(): Boolean {
        return _uiState.value.allyHp > 0
    }

    fun reset() {
        _uiState.update {
            it.copy(
                ennemy = null,
                ally = null,
                allyHp = 0,
                ennemyHp = 0,
                isFinished = false,
                isFighting = false,
                lvl = 1,
                pierres = 1
            )
        }
    }


}

data class CombatUiState(
    val ennemy: Pokemon ?= null,
    val ally: Pokemon ?= null,
    val allyHp: Int = 0,
    val ennemyHp: Int = 0,
    val isFighting: Boolean = false,
    val isFinished: Boolean = false,
    val lvl: Int = 1,
    val pierres: Int = 1
)