import { Component } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { AutocompleteSearchDropdownComponent } from '../../common/autocomplete-search-dropdown/autocomplete-search-dropdown.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { EditSongComponent } from '../edit-song/edit-song.component';
import { CreateSongComponent } from '../create-song/create-song.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";

@Component({
  selector: 'app-details-song',
  imports: [FooterComponent,
    HeaderComponent,
    NavbarComponent,
    CommonModule,
    MatDialogModule,
    FormsModule,
    AutocompleteSearchDropdownComponent, SidebarComponent],
  templateUrl: './details-song.component.html',
  styleUrl: './details-song.component.css'
})
export class DetailsSongComponent {
constructor(private dialog: MatDialog) {}

  openEditArtistDialog(): void {
    const dialogRef = this.dialog.open(EditSongComponent, {
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
