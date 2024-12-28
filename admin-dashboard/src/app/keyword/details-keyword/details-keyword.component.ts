import { Component } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { AutocompleteSearchDropdownComponent } from '../../common/autocomplete-search-dropdown/autocomplete-search-dropdown.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { EditKeywordComponent } from '../edit-keyword/edit-keyword.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
@Component({
  selector: 'app-details-keyword',
  imports: [FooterComponent,
    HeaderComponent,
    NavbarComponent,
    MatDialogModule,
    FormsModule,
    AutocompleteSearchDropdownComponent, SidebarComponent],
  templateUrl: './details-keyword.component.html',
  styleUrl: './details-keyword.component.css'
})
export class DetailsKeywordComponent {
 constructor(private dialog: MatDialog) {}

  openEditGenreDialog(): void {
    const dialogRef = this.dialog.open(EditKeywordComponent, {
      width: '800px', 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
       
        console.log('User added successfully');
      }
    });
  }
}
