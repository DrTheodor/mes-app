package dev.drtheo.mes.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.drtheo.mes.MainApplication
import dev.drtheo.mes.data.AuthRepository
import dev.drtheo.mes.data.DnevnikRepository
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.profile.Profile
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Date

sealed interface AuthUiState {
    data object Authenticated : AuthUiState
    data object Unauthenticated : AuthUiState
}

sealed interface ScheduleUiState {
    data class Success(val events: List<Event>) : ScheduleUiState
    data object Error : ScheduleUiState
    data object Loading : ScheduleUiState
}

sealed interface HomeworkUiState {
    data class Success(val homework: List<Event>) : HomeworkUiState
    data object Error : HomeworkUiState
    data object Loading : HomeworkUiState
}

sealed interface MarksUiState {
    data class Success(val homework: List<Event>) : MarksUiState
    data object Error : MarksUiState
    data object Loading : MarksUiState
}

class DnevnikViewModel(
    private val authRepository: AuthRepository,
    private val dnevnikRepository: DnevnikRepository
) : ViewModel() {
    var profile: Profile? by mutableStateOf(null)
        private set

    var authUiState: AuthUiState by mutableStateOf(AuthUiState.Unauthenticated)

    /** The mutable State that stores the status of the most recent request */
    var scheduleUiState: ScheduleUiState by mutableStateOf(ScheduleUiState.Loading)
        private set

    var homeworkUiState: HomeworkUiState by mutableStateOf(HomeworkUiState.Loading)
        private set

    var marksUiState: MarksUiState by mutableStateOf(MarksUiState.Loading)
        private set

    private val date: Date = Date() //SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse("2025-02-14")!!

    var selectedEvent: Event? by mutableStateOf(null)
        private set

    fun selectedEvent(event: Event) {
        selectedEvent = event
    }

    init {
        refreshAccount()
    }

    private fun refreshAccount() {
        authUiState = AuthUiState.Unauthenticated

        if (authRepository.hasToken()) {
            authUiState = AuthUiState.Authenticated
            refreshData(refreshProfile = true)
        }
    }

    fun login(token: String) {
        authRepository.saveToken(token)
        refreshAccount()
    }

    private fun getProfile(): Job = viewModelScope.launch { updateProfile() }

    private suspend fun updateProfile() {
        profile = null

        try {
            profile = dnevnikRepository.getProfile()
        } catch (e: Exception) {
            Log.e("Dnevnik", "Failed to update profile!")
            e.printStackTrace()
        }
    }

    fun refreshData(refreshProfile: Boolean = false) {
        viewModelScope.launch {
            scheduleUiState = ScheduleUiState.Loading
            homeworkUiState = HomeworkUiState.Loading
            marksUiState = MarksUiState.Loading

            try {
                if (profile == null || refreshProfile) {
                    updateProfile()

                    if (profile == null)
                        throw IllegalArgumentException("Profile can't be null!")
                }

                println("Profile: $profile")

                val allEvents = dnevnikRepository.getEvents(profile!!, date, expandFields = "homework,marks")

                allEvents.response.forEach {
                    println(it)
                }

                scheduleUiState = ScheduleUiState.Success(
                    allEvents.response
                )

                homeworkUiState = HomeworkUiState.Success(
                    allEvents.response.filter {
                        it.homework != null && !it.homework.isEmpty()
                    }
                )

                marksUiState = MarksUiState.Success(
                    allEvents.response.filter {
                        !it.marks.isNullOrEmpty()
                    }
                )

                return@launch
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            scheduleUiState = ScheduleUiState.Error
            homeworkUiState = HomeworkUiState.Error
            marksUiState = MarksUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)

                val authRepository = application.container.authRepository
                val eventsRepository = application.container.dnevnikRepository

                DnevnikViewModel(
                    authRepository = authRepository,
                    dnevnikRepository = eventsRepository
                )
            }
        }
    }
}