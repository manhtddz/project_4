import { Component } from '@angular/core';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateKeywordComponent } from '../create-keyword/create-keyword.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { KeywordService } from '../../services/keyword.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-overview-keyword',
  imports: [FooterComponent, NavbarComponent, HeaderComponent, RouterLink, SidebarComponent,CommonModule],
  templateUrl: './overview-keyword.component.html',
  styleUrl: './overview-keyword.component.css'
})
export class OverviewKeywordComponent {
  data: any;
     constructor(private dialog: MatDialog,private keywordService : KeywordService) {}
     ngOnInit(): void {
      this.loadData();
     }
     loadData(): void {
       this.keywordService.fetchKeywords().subscribe((response: any) => {
         this.data = response;
       });
     }

openAddKeywordDialog(): void {
    const dialogRef = this.dialog.open(CreateKeywordComponent, {
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
