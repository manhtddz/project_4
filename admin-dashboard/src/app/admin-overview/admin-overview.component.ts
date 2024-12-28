import { CommonModule } from '@angular/common';
import { Component, ViewChild, ViewEncapsulation, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../common/header/header.component';
import { FooterComponent } from '../common/footer/footer.component';
import { NavbarComponent } from '../common/navbar/navbar.component';
import { SidebarComponent } from "../common/sidebar/sidebar.component";
import { BieudoComponent } from '../bieudo/bieudo.component';
// import { HttpClient } from '@angular/common/http';
// import { TransactionService } from '../Services/transaction.service';
// import { Transaction, Transactions } from '../../types';
// import { Paginator, PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'admin-overview',
  standalone: true,
//  imports: [NavbarComponent, HeaderComponent, FooterComponent, RouterLink, RouterOutlet, CommonModule, PaginatorModule],
  imports: [NavbarComponent, HeaderComponent, FooterComponent, CommonModule, SidebarComponent,BieudoComponent,SidebarComponent],
  templateUrl: './admin-overview.component.html',
  styleUrl: './admin-overview.component.css'
})
export class AdminOverviewComponent {

  // constructor(private transService: TransactionService) { }

  // transaction: Transaction[] = [];

  // @ViewChild('paginator') paginator: Paginator | undefined;

  // totalRecords: number = 0;
  // rows: number = 5;

  // todayMoney: number;
  // todayUser: number;
  // todayClient: number;
  // todaySale: number;

  // ngOnInit(): void {
  //   this.loadTransactions(0, this.rows);
  //   this.loadClient();
  //   this.loadMoney();
  //   this.loadSale();
  //   this.loadUser();
  // }

  // resetPaginator() {
  //   this.paginator?.changePage(0);
  // }

  // onPageChange(event: any) {
  //   this.loadTransactions(event.page, event.rows);
  // }

  // loadTransactions(page: number, perPage: number) {
  //   this.transService.getTransactions('https://localhost:7201/api/Transaction', { page, perPage })
  //     .subscribe({next: (data: Transactions) => {
  //       this.transaction = data.items;
  //       this.totalRecords = data.total;
  //     },
  //       error: (err) => {
  //         console.log(err);
  //       },
  //     });
  // }

  // loadUser() {
  //   this.transService.getTodayUser().subscribe({
  //     next: (user: any) => {
  //       this.todayUser = user;
  //     }
  //   })
  // }

  // loadClient() {
  //   this.transService.getTodayClient().subscribe({
  //     next: (client: any) => {
  //       this.todayClient = client;
  //     }
  //   })
  // }

  // loadSale() {
  //   this.transService.getTodaySale().subscribe({
  //     next: (sale: any) => {
  //       this.todaySale = sale;
  //     }
  //   })
  // }

  // loadMoney() {
  //   this.transService.getTodayMoney().subscribe({
  //     next: (money: any) => {
  //       this.todayMoney = money;
  //     }
  //   })
  // }

  // getFormattedCurrency(formatValue: number) {
  //   const formatter = new Intl.NumberFormat('vi-VN', {
  //     style: 'currency',
  //     currency: 'VND'
  //   });
  //   return formatter.format(formatValue);
  // }
}
