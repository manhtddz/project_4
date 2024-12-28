import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateNewsComponent } from '../create-news/create-news.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { CommonModule } from '@angular/common';
import { NewsService } from '../../services/news.service';
@Component({
  selector: 'app-overview-news',
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-news.component.html',
  styleUrl: './overview-news.component.css'
})
export class OverviewNewsComponent implements OnInit {
data: any;
   constructor(private dialog: MatDialog,private newsService : NewsService) {}
   ngOnInit(): void {
    this.loadData();
   }
   loadData(): void {
     this.newsService.fetchNews().subscribe((response: any) => {
       this.data = response;
     });
   }

  openAddNewsDialog(): void {
    const dialogRef = this.dialog.open(CreateNewsComponent, {
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
