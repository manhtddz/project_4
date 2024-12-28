import { Component } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { EditGenreComponent } from '../edit-genre/edit-genre.component';
import { AutocompleteSearchDropdownComponent } from '../../common/autocomplete-search-dropdown/autocomplete-search-dropdown.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { CreateSongComponent } from '../../song/create-song/create-song.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";

@Component({
  selector: 'app-details-genre',
  standalone:true,
  imports: [FooterComponent,
    HeaderComponent,
    NavbarComponent,
    RouterLink,
    CommonModule,
    MatDialogModule,
    FormsModule,
    AutocompleteSearchDropdownComponent, SidebarComponent],
  templateUrl: './details-genre.component.html',
  styleUrl: './details-genre.component.css'
})
export class DetailsGenreComponent {
 constructor(private dialog: MatDialog) {}

  openEditGenreDialog(): void {
    const dialogRef = this.dialog.open(EditGenreComponent, {
      width: '800px', 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
       
        console.log('User added successfully');
      }
    });
  }

  openAddNewSongToSubjectDialog(): void {
    const dialogRef = this.dialog.open(CreateSongComponent, {
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
