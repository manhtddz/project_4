import { Component } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditSubjectComponent } from '../edit-subject/edit-subject.component';
import { CreateAlbumComponent } from '../../album/create-album/create-album.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutocompleteSearchDropdownComponent } from '../../common/autocomplete-search-dropdown/autocomplete-search-dropdown.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";

@Component({
  selector: 'app-details-subject',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    NavbarComponent,
    RouterLink,
    CommonModule,
    MatDialogModule,
    FormsModule,
    AutocompleteSearchDropdownComponent,
    SidebarComponent
],
  templateUrl: './details-subject.component.html',
  styleUrl: './details-subject.component.css'
})
export class DetailsSubjectComponent {
  constructor(private dialog: MatDialog) {}

  openEditSubjectDialog(): void {
    const dialogRef = this.dialog.open(EditSubjectComponent, {
      width: '800px', 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
       
        console.log('User added successfully');
      }
    });
  }

  openAddNewAlbumToSubjectDialog(): void {
    const dialogRef = this.dialog.open(CreateAlbumComponent, {
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
