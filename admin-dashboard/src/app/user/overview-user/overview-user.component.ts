import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../common/footer/footer.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { HeaderComponent } from '../../common/header/header.component';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Replace5CharsPipe } from '../../services/replace5-chars.pipe';
import { SidebarComponent } from '../../common/sidebar/sidebar.component';
import { Paginator, PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-overview-user',
  standalone: true,
  imports: [
    FooterComponent,
    NavbarComponent,
    HeaderComponent,
    RouterLink,
    CommonModule,
    PaginatorModule,
    Replace5CharsPipe,
    SidebarComponent,
  ],
  templateUrl: './overview-user.component.html',
  styleUrl: './overview-user.component.css',
})
export class OverviewUserComponent implements OnInit {
  data: any;
  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.loadUsers();
  }
  loadUsers(): void {
    this.userService.fetchUsers().subscribe((response: any) => {
      this.data = response;
    });
  }
}
