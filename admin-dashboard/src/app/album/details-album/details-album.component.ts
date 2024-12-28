import { Component } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { AutocompleteSearchDropdownComponent } from '../../common/autocomplete-search-dropdown/autocomplete-search-dropdown.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { EditAlbumComponent } from '../edit-album/edit-album.component';
import { CreateSongComponent } from '../../song/create-song/create-song.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
@Component({
  selector: 'app-details-album',
  standalone: true,
  imports: [FooterComponent,
    HeaderComponent,
    NavbarComponent,
    RouterLink,
    CommonModule,
    MatDialogModule,
    FormsModule,
    AutocompleteSearchDropdownComponent, SidebarComponent],
  templateUrl: './details-album.component.html',
  styleUrl: './details-album.component.css'
})
export class DetailsAlbumComponent {
constructor(private dialog: MatDialog) {}

  openEditArtistDialog(): void {
    const dialogRef = this.dialog.open(EditAlbumComponent, {
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
