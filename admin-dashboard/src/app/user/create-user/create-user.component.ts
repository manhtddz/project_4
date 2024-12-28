import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  FormControl,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../../common/header/header.component';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { FooterComponent } from '../../common/footer/footer.component';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from '../../../jwt.interceptor';
import { SidebarComponent } from '../../common/sidebar/sidebar.component';
import { UploadImageService } from '../../services/upload-image.service';

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    ReactiveFormsModule,
    HeaderComponent,
    NavbarComponent,
    FooterComponent,
    SidebarComponent,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
  ],
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
})
export class CreateUserComponent implements OnInit {
  submitted = false;
  selectedFile: File | null = null;
  imageError: string | null = null;

  get f(): { [key: string]: AbstractControl } {
    return this.formCreateUser.controls;
  }
  formCreateUser: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    role: new FormControl(''),
    avatar: new FormControl(''),
    phone: new FormControl(''),
    email: new FormControl(''),
    dob: new FormControl(''),
    fullName: new FormControl(''),
  });

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private router: Router,
    private uploadImage: UploadImageService
  ) {}

  ngOnInit(): void {
    this.formCreateUser = this.formBuilder.group({
      role: ['', Validators.required],
      username: ['', [Validators.required]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(/^(?=.*[\W]).{8,}$/),
        ],
      ],
      fullName: ['', Validators.required],
      avatar: ['', Validators.required],
      dob: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]],
      email: ['', [Validators.required, Validators.email]],
    });
  }
  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      if (!file.type.match('image.*')) {
        this.imageError = 'Only image files are allowed. Choose again!';
        this.selectedFile = null;
      } else {
        this.selectedFile = file;
        this.imageError = null;
      }
    }
  }

  createUser() {
    this.submitted = true;
    this.uploadImage.uploadImage(this.selectedFile!).subscribe({
      next: (stringNameAvatar) => {
        this.formCreateUser.controls['avatar'].setValue(stringNameAvatar);
        if (this.formCreateUser.invalid) {
          return;
        }
        const formData = this.formCreateUser.value;
        this.userService.createUser(formData).subscribe({
          next: (res) => {
            this.router.navigate(['/users']);
            this.toastr.success('User created successfully!', 'success');
          },
          error: (err) => {
            //  console.log('Error creating user:', JSON.stringify(err.errors, null, 2));
            if (err.status === 400 && Array.isArray(err.errors)) {
              err.errors.forEach((e: any) => {
                if (e.usernameError) {
                  this.formCreateUser.controls['username'].setErrors({
                    backendError: e.usernameError,
                  });
                }
                if (e.phoneError) {
                  this.formCreateUser.controls['phone'].setErrors({
                    backendError: e.phoneError,
                  });
                }
                if (e.emailError) {
                  this.formCreateUser.controls['email'].setErrors({
                    backendError: e.emailError,
                  });
                }
                if (e.passwordError) {
                  this.formCreateUser.controls['password'].setErrors({
                    backendError: e.passwordError,
                  });
                }
              });
            }
          },
        });
      },
    });
  }
}
