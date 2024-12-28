import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { CreateSubjectComponent } from '../create-subject/create-subject.component';
import { MatDialog } from '@angular/material/dialog';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { EditSubjectComponent } from '../edit-subject/edit-subject.component';
import { SidebarComponent } from '../../common/sidebar/sidebar.component';
import { CommonModule } from '@angular/common';
import { SongService } from '../../services/song.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-overview-subject',
  imports: [
    FooterComponent,
    NavbarComponent,
    HeaderComponent,
    RouterLink,
    SidebarComponent,
    CommonModule,
  ],
  templateUrl: './overview-subject.component.html',
  styleUrl: './overview-subject.component.css',
})
export class OverviewSubjectComponent implements OnInit {
  data: any;
  constructor(
    private dialog: MatDialog,
    private categoryService: CategoryService
  ) {}
  ngOnInit(): void {
    this.loadData();
  }
  loadData(): void {
    this.categoryService.fetchCategories().subscribe((response: any) => {
      this.data = response;
    });
  }

  openAddSubjectDialog(): void {
    const dialogRef = this.dialog.open(CreateSubjectComponent, {
      width: '800px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('Dialog result:', result);
      if (result === 'saved') {
        console.log('User added successfully');
      }
    });
  }
}
