package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.dao.EnterpriseDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseExpertConnectionDao
import com.ignitech.esgcompanion.data.local.entity.EnterpriseEntity
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertEnterprise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertEnterpriseViewModel @Inject constructor(
    private val enterpriseDao: EnterpriseDao,
    private val connectionDao: EnterpriseExpertConnectionDao
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Get all enterprises from database
    val enterprises: StateFlow<List<ExpertEnterprise>> = enterpriseDao.getAllEnterprises()
        .map { entities -> entities.map { it.toExpertEnterprise() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Get connected enterprises (mock for now - would need expert ID from auth)
    private val _connectedEnterprises = MutableStateFlow<List<ExpertEnterprise>>(emptyList())
    val connectedEnterprises: StateFlow<List<ExpertEnterprise>> = _connectedEnterprises.asStateFlow()

    init {
        loadConnectedEnterprises()
    }

    private fun loadConnectedEnterprises() {
        viewModelScope.launch {
            // Mock: get first 3 enterprises as connected
            // In real app, would use expert ID from auth to get actual connections
            enterpriseDao.getAllEnterprises()
                .map { entities -> 
                    entities.take(3).map { it.toExpertEnterprise() }
                }
                .collect { connected ->
                    _connectedEnterprises.value = connected
                }
        }
    }

    fun selectTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }

    fun connectToEnterprise(enterpriseId: String) {
        viewModelScope.launch {
            val enterprise = enterprises.value.find { it.id == enterpriseId }
            enterprise?.let {
                val currentConnected = _connectedEnterprises.value.toMutableList()
                if (!currentConnected.any { connected -> connected.id == enterpriseId }) {
                    currentConnected.add(it)
                    _connectedEnterprises.value = currentConnected
                    
                    // TODO: Save connection to database
                    // connectionDao.insertConnection(...)
                }
            }
        }
    }

    fun disconnectFromEnterprise(enterpriseId: String) {
        viewModelScope.launch {
            val currentConnected = _connectedEnterprises.value.toMutableList()
            currentConnected.removeAll { it.id == enterpriseId }
            _connectedEnterprises.value = currentConnected
            
            // TODO: Remove connection from database
            // connectionDao.deleteConnection(...)
        }
    }

    fun startConsultation(enterpriseId: String) {
        viewModelScope.launch {
            // TODO: Navigate to consultation screen or show consultation options
            println("Starting consultation with enterprise: $enterpriseId")
        }
    }

    // Extension function to convert EnterpriseEntity to ExpertEnterprise
    private fun EnterpriseEntity.toExpertEnterprise(): ExpertEnterprise {
        return ExpertEnterprise(
            id = this.id,
            name = this.name,
            industry = this.industry,
            location = this.location,
            description = this.description,
            size = this.size,
            esgScore = this.esgScore
        )
    }
}
