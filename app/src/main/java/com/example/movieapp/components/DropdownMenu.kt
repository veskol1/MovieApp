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
import androidx.compose.ui.unit.dp
import com.example.movieapp.viewmodel.FilterType

@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    selectedFilterText: String,
    filterOptions: List<String>,
    onFilterChange: (FilterType) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier
        .wrapContentSize()
        .padding(vertical = 16.dp)) {
        Text(
            text = "Sort by $selectedFilterText",
            modifier = Modifier.clickable { expanded = true }
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filterOptions.forEach { filterText ->
                DropdownMenuItem(
                    text = { Text(text = filterText) },
                    onClick = {
                        expanded = false
                        onFilterChange(FilterType.valueOf(filterText))
                    }
                )
            }
        }
    }
}