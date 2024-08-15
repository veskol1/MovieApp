package com.example.movieapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.movieapp.viewmodel.FilterType
import kotlin.enums.EnumEntries

@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    selectedFilterText: String,
    filterOptions: EnumEntries<FilterType>,
    onFilterChange: (FilterType) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier
        .wrapContentSize()
        .padding(vertical = 16.dp)) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Sort by ")
            }
            append(selectedFilterText)
        }, modifier = Modifier.clickable { expanded = true })

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filterOptions.forEach { filterType ->
                DropdownMenuItem(
                    text = { Text(text = filterType.title) },
                    onClick = {
                        expanded = false
                        onFilterChange(filterType)
                    }
                )
            }
        }
    }
}