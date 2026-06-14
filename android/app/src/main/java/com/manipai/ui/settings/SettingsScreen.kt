package com.manipai.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val stats by viewModel.stats.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings", 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                "USER STATISTICS", 
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Conversations",
                    value = (stats?.conversationsCount ?: 0).toString(),
                    icon = Icons.Default.ChatBubbleOutline
                )
                Spacer(Modifier.width(12.dp))
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Messages",
                    value = (stats?.messagesCount ?: 0).toString(),
                    icon = Icons.Default.BarChart
                )
            }
            
            Spacer(Modifier.height(32.dp))
            
            Text(
                "PREFERENCES", 
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(12.dp))
            
            PreferenceItem(
                label = "Theme",
                value = settings.theme.uppercase(),
                icon = Icons.Default.Palette,
                onClick = {
                    val newTheme = if (settings.theme == "dark") "light" else "dark"
                    viewModel.updateTheme(newTheme)
                }
            )
            
            PreferenceItem(
                label = "Language",
                value = settings.language.uppercase(),
                icon = Icons.Default.Language,
                onClick = {
                    val newLang = if (settings.language == "en") "es" else "en"
                    viewModel.updateLanguage(newLang)
                }
            )
            
            Spacer(Modifier.height(32.dp))
            
            Text(
                "AI SETTINGS", 
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(12.dp))

            var provider by remember(settings.ai.provider) { mutableStateOf(settings.ai.provider) }
            var baseUrl by remember(settings.ai.baseUrl) { mutableStateOf(settings.ai.baseUrl) }
            var apiKey by remember(settings.ai.apiKey) { mutableStateOf(settings.ai.apiKey) }
            var modelName by remember(settings.ai.model) { mutableStateOf(settings.ai.model) }

            val providers = listOf("anthropic", "openai", "nvidia", "custom")
            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = provider.replaceFirstChar { it.uppercase() },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Provider") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )
                Surface(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(top = 8.dp),
                    color = androidx.compose.ui.graphics.Color.Transparent,
                    onClick = { expanded = !expanded }
                ) {}
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    providers.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p.replaceFirstChar { it.uppercase() }) },
                            onClick = {
                                provider = p
                                expanded = false
                                if (p == "nvidia" && baseUrl.isEmpty()) baseUrl = "https://integrate.api.nvidia.com/v1"
                                else if (p == "openai" && baseUrl.isEmpty()) baseUrl = "https://api.openai.com/v1"
                                viewModel.updateAiSettings(provider, baseUrl, apiKey, modelName)
                            }
                        )
                    }
                }
            }

            if (provider != "anthropic") {
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = baseUrl,
                    onValueChange = { 
                        baseUrl = it
                        viewModel.updateAiSettings(provider, baseUrl, apiKey, modelName)
                    },
                    label = { Text("Base URL") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("https://api.openai.com/v1") }
                )
                
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { 
                        apiKey = it
                        viewModel.updateAiSettings(provider, baseUrl, apiKey, modelName)
                    },
                    label = { Text("API Key") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("sk-...") }
                )
                
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = modelName,
                    onValueChange = { 
                        modelName = it
                        viewModel.updateAiSettings(provider, baseUrl, apiKey, modelName)
                    },
                    label = { Text("Model Name") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("gpt-3.5-turbo") }
                )
            }
            
            Spacer(Modifier.height(32.dp))
            
            Divider(color = MaterialTheme.colorScheme.outlineVariant)
            
            Spacer(Modifier.height(16.dp))
            
            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Logout from ManipAI", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon, 
                contentDescription = null, 
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun PreferenceItem(
    label: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, 
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
