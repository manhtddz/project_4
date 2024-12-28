import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../common/footer/footer.component";
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { CreateGenreComponent } from '../create-genre/create-genre.component';
import { MatDialog } from '@angular/material/dialog';
import { HeaderComponent } from "../../common/header/header.component";
import { RouterLink } from '@angular/router';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { GenreService } from '../../services/genre.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-overview-genre',
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  standalone:true,
  templateUrl: './overview-genre.component.html',
  styleUrl: './overview-genre.component.css'
})
export class OverviewGenreComponent implements OnInit {
 data: any;
   constructor(private dialog: MatDialog,private genreService : GenreService) {}
   ngOnInit(): void {
    this.loadData();
   }
   loadData(): void {
     this.genreService.fetchGenres().subscribe((response: any) => {
       this.data = response;
     });
   }

  openAddGenreDialog(): void {
    const dialogRef = this.dialog.open(CreateGenreComponent, {
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
