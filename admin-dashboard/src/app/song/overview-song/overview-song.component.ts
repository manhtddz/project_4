import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { RouterLink } from '@angular/router';
import { CreateSongComponent } from '../create-song/create-song.component';
import { MatDialog } from '@angular/material/dialog';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { CommonModule } from '@angular/common';
import { SongService } from '../../services/song.service';

@Component({
  selector: 'app-overview-song',
  imports: [FooterComponent, HeaderComponent, NavbarComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-song.component.html',
  styleUrl: './overview-song.component.css'
})
export class OverviewSongComponent implements OnInit {
  data: any;
     constructor(private dialog: MatDialog,private songService : SongService) {}
     ngOnInit(): void {
      this.loadData();
     }
     loadData(): void {
       this.songService.fetchSongs().subscribe((response: any) => {
         this.data = response;
       });
     }

  openAddUserDialog(): void {
    const dialogRef = this.dialog.open(CreateSongComponent, {
      width: '1000px', // Bạn có thể tùy chỉnh kích thước
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
        // Thực hiện hành động sau khi dialog lưu user
        console.log('User added successfully');
      }
    });
  }
}
