package dev.drtheo.mes.ui

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
import dev.drtheo.mes.formatToMes
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.profile.Profile
import dev.drtheo.mes.model.wrapped.DnevnikData
import dev.drtheo.mes.model.wrapped.Homework
import dev.drtheo.mes.model.wrapped.HomeworkData
import dev.drtheo.mes.model.wrapped.MarkData
import dev.drtheo.mes.same
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar

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
    data class Success(val homework: List<Homework>) : HomeworkUiState
    data object Error : HomeworkUiState
    data object Loading : HomeworkUiState
}

sealed interface MarksUiState {
    data class Success(val homework: List<MarkData>) : MarksUiState
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

    var scheduleUiState: ScheduleUiState by mutableStateOf(ScheduleUiState.Loading)
        private set

    var homeworkUiState: HomeworkUiState by mutableStateOf(HomeworkUiState.Loading)
        private set

    var marksUiState: MarksUiState by mutableStateOf(MarksUiState.Loading)
        private set

    var date: Calendar by mutableStateOf(Calendar.getInstance())
        private set

    var selectedEvent: Event? by mutableStateOf(null)
        private set

    private val cache: MutableMap<Calendar, DnevnikData> = hashMapOf()

    fun date(date: Calendar) {
        this.date = date.clone() as Calendar
        this.refreshData(silent = true)
    }

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

    private fun setEvents(date: Calendar, data: DnevnikData) {
        if (!this.date.same(date))
            return

        cache[date] = data

        data.events.forEach {
            Log.d("Dnevnik", it.toString())
        }

        scheduleUiState = ScheduleUiState.Success(data.events)
        homeworkUiState = HomeworkUiState.Success(data.homework)
        marksUiState = MarksUiState.Success(data.marks)
    }

    private fun setEvents(date: Calendar, events: List<Event>) {
        val homework = HomeworkData.from(events)
        val marks = MarkData.from(events)

        setEvents(date, DnevnikData(events, homework, marks))
    }

    private fun restoreFromCache(date: Calendar): Boolean {
        val events = cache[date]

        if (events != null)
            this.setEvents(date, events)

        return events != null
    }

    fun refreshData(silent: Boolean = false, refreshProfile: Boolean = false) {
        viewModelScope.launch {
            val date = date
            val hasCache = restoreFromCache(date)

            if (!silent || !hasCache) {
                scheduleUiState = ScheduleUiState.Loading
                homeworkUiState = HomeworkUiState.Loading
                marksUiState = MarksUiState.Loading
            }

            try {
                if (profile == null || refreshProfile)
                    updateProfile()

                if (profile == null)
                    updateProfile()

                if (profile == null)
                    throw IllegalArgumentException("Profile can't be null!")

                Log.d("Dnevnik", "Profile: $profile")

                val allEvents = dnevnikRepository.getEvents(
                    profile!!, date.formatToMes(),
                    expandFields = "homework,marks"
                )

                setEvents(date, allEvents.response)
                return@launch
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (!hasCache) {
                scheduleUiState = ScheduleUiState.Error
                homeworkUiState = HomeworkUiState.Error
                marksUiState = MarksUiState.Error
            }
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