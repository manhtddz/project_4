import { Component } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-keyword',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './create-keyword.component.html',
  styleUrl: './create-keyword.component.css'
})
export class CreateKeywordComponent {
constructor(public dialogRef: MatDialogRef<CreateKeywordComponent>) {}

  saveGenre(): void {
    // Logic để lưu user mới
    console.log('User saved!');
    this.dialogRef.close('saved');
  }

}
