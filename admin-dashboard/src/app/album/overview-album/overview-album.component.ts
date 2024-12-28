import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { MatDialog } from '@angular/material/dialog';
import { HeaderComponent } from "../../common/header/header.component";
import { RouterLink } from '@angular/router';
import { CreateAlbumComponent } from '../create-album/create-album.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { AlbumService } from '../../services/album.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-overview-album',
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-album.component.html',
  styleUrl: './overview-album.component.css'
})
export class OverviewAlbumComponent implements OnInit {
  data: any;
  constructor(private dialog: MatDialog,private albumService : AlbumService) {}
  ngOnInit(): void {
   this.loadAlbums();
  }
  loadAlbums(): void {
    this.albumService.fetchAlbums().subscribe((response: any) => {
      this.data = response;
    });
  }



  openAddAlbumDialog(): void {
    const dialogRef = this.dialog.open(CreateAlbumComponent, {
      width: '800px',height: '800px',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
       
        console.log('User added successfully');
      }
    });
  }
}
