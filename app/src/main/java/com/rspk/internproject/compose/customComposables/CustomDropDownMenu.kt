package com.rspk.internproject.compose.customComposables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    list: List<String>,
    textFieldComposable: @Composable (modifier: Modifier) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .padding(end = 10.dp)
    ) {
        textFieldComposable(
            Modifier
                .menuAnchor(
                    type = MenuAnchorType.PrimaryEditable,
                    enabled = true
                )
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .heightIn(max = 180.dp)
                .fillMaxWidth(0.5f),
            shape = RoundedCornerShape(10.dp),
            containerColor = colorResource(id = R.color.text_field_container)
        ) {
            list.filter {
                it.contains(textFieldValue, ignoreCase = true)
            }.forEach {
                DropdownMenuItem(
                    text = { Text(text = it, fontSize = 20.sp) },
                    onClick = {
                        onTextFieldValueChange(it)
                        onExpandedChange(false)
                    },
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.inverseSurface,
                    )
                )
            }
        }
    }
}