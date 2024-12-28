import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreatePlaylistComponent } from '../create-playlist/create-playlist.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { CommonModule } from '@angular/common';
import { PlaylistService } from '../../services/playlist.service';

@Component({
  selector: 'app-overview-playlist',
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-playlist.component.html',
  styleUrl: './overview-playlist.component.css'
})
export class OverviewPlaylistComponent implements OnInit {
data: any;
   constructor(private dialog: MatDialog,private playlistService : PlaylistService) {}
   ngOnInit(): void {
    this.loadData();
   }
   loadData(): void {
     this.playlistService.fetchPlaylists().subscribe((response: any) => {
       this.data = response;
     });
   }

  openAddPlaylistDialog(): void {
    const dialogRef = this.dialog.open(CreatePlaylistComponent, {
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
