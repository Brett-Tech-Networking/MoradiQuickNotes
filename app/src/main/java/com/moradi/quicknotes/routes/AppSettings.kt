
@file:OptIn(ExperimentalMaterialApi::class)
package com.moradi.quicknotes.routes

import androidx.annotation.ArrayRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.farmerbb.notepad.R
import com.farmerbb.notepad.models.Prefs
import com.farmerbb.notepad.ui.widgets.AppBarText
import com.farmerbb.notepad.ui.widgets.BackButton
import com.farmerbb.notepad.utils.dataStore
import de.schnettler.datastore.compose.model.Preference.PreferenceItem.ListPreference
import de.schnettler.datastore.compose.model.Preference.PreferenceItem.SwitchPreference
import de.schnettler.datastore.compose.ui.PreferenceScreen
import de.schnettler.datastore.manager.DataStoreManager

@Composable fun AppSettings(
    navController: NavController? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { BackButton(navController) },
                title = { AppBarText(stringResource(id = R.string.action_settings)) },
                backgroundColor = colorResource(id = R.color.primary)
            )
        },
        content = { NotepadPreferenceScreen() }
    )
}

@Composable fun NotepadPreferenceScreen() {
    val dataStore = LocalContext.current.dataStore
    val dataStoreManager = remember { DataStoreManager(dataStore) }

    PreferenceScreen(
        items = listOf(
            ListPreference(
                request = Prefs.Theme,
                title = stringResource(id = R.string.action_theme),
                summary = "",
                singleLineTitle = true,
                icon = {},
                entries = listPrefEntries(
                    keyRes = R.array.theme_list_values,
                    valueRes = R.array.theme_list
                ),
            ),
            ListPreference(
                request = Prefs.FontSize,
                title = stringResource(id = R.string.action_font_size),
                summary = "",
                singleLineTitle = true,
                icon = {},
                entries = listPrefEntries(
                    keyRes = R.array.font_size_list_values,
                    valueRes = R.array.font_size_list
                ),
            ),
            ListPreference(
                request = Prefs.SortBy,
                title = stringResource(id = R.string.action_sort_by),
                summary = "",
                singleLineTitle = true,
                icon = {},
                entries = listPrefEntries(
                    keyRes = R.array.sort_by_list_values,
                    valueRes = R.array.sort_by_list
                ),
            ),
            ListPreference(
                request = Prefs.ExportFilename,
                title = stringResource(id = R.string.action_export_filename),
                summary = "",
                singleLineTitle = true,
                icon = {},
                entries = listPrefEntries(
                    keyRes = R.array.exported_filename_list_values,
                    valueRes = R.array.exported_filename_list
                ),
            ),
            SwitchPreference(
                request = Prefs.ShowDialogs,
                title = stringResource(id = R.string.pref_title_show_dialogs),
                summary = "",
                singleLineTitle = true,
                icon = {}
            ),
            SwitchPreference(
                request = Prefs.ShowDate,
                title = stringResource(id = R.string.pref_title_show_date),
                summary = "",
                singleLineTitle = true,
                icon = {}
            ),
            SwitchPreference(
                request = Prefs.DirectEdit,
                title = stringResource(id = R.string.pref_title_direct_edit),
                summary = "",
                singleLineTitle = true,
                icon = {}
            ),
            SwitchPreference(
                request = Prefs.Markdown,
                title = stringResource(id = R.string.pref_title_markdown),
                summary = "",
                singleLineTitle = true,
                icon = {}
            )
        ),
        contentPadding = PaddingValues(0.dp),
        dataStoreManager = dataStoreManager
    )
}

@ReadOnlyComposable
@Composable fun listPrefEntries(
    @ArrayRes keyRes: Int,
    @ArrayRes valueRes: Int
): Map<String, String> {
    val keys = stringArrayResource(id = keyRes)
    val values = stringArrayResource(id = valueRes)

    if(keys.size != values.size) {
        throw RuntimeException("Keys and values are not the same size")
    }

    val map = mutableMapOf<String, String>()
    for(i in keys.indices) {
        map[keys[i]] = values[i]
    }

    return map.toMutableMap()
}

@Suppress("FunctionName")
fun NavGraphBuilder.AppSettingsRoute(
    navController: NavController
) = composable(route = "AppSettings") {
    AppSettings(navController)
}

fun NavController.appSettings() = navigate("AppSettings")

@Preview @Composable fun AppSettingsPreview() = MaterialTheme {
    AppSettings()
}