import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../common/header/header.component';
import { FooterComponent } from '../../common/footer/footer.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";

@Component({
  selector: 'app-details-user',
  imports: [FormsModule, NavbarComponent, HeaderComponent, FooterComponent, CommonModule, NavbarComponent, SidebarComponent],
  templateUrl: './details-user.component.html',
  styleUrl: './details-user.component.css'
})
export class DetailsUserComponent implements OnInit {
  
  constructor() { }
  ngOnInit(): void {
   
  }

}
