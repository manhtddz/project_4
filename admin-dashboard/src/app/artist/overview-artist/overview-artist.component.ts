import { Component, OnInit } from '@angular/core';
import { CreateArtistComponent } from '../create-artist/create-artist.component';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { PlaylistService } from '../../services/playlist.service';
import { ArtistService } from '../../services/artist.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-overview-artist',
  standalone: true,
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-artist.component.html',
  styleUrl: './overview-artist.component.css'
})
export class OverviewArtistComponent implements OnInit {
data: any;
  constructor(private dialog: MatDialog,private artistService : ArtistService) {}
  ngOnInit(): void {
   this.loadArtists();
  }
  loadArtists(): void {
    this.artistService.fetchArtists().subscribe((response: any) => {
      this.data = response;
    });
  }
  openAddArtistDialog(): void {
    const dialogRef = this.dialog.open(CreateArtistComponent, {
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
