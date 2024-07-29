import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user';


function nameValidator(control: FormControl): {[s: string]: boolean} {
  if(!control.value?.match("^[a-zA-Z ,.'-]{2,30}$")) {
    return {invalid: true};
  } else {
    return {invalid: false};
  }
}

function usernameValidator(control: FormControl): {[s: string]: boolean} {
  if(!control.value?.match("^[a-zA-Z0-9._-]{5,15}$")) {
    return {invalid: true};
  } else {
    return {invalid: false};
  }
}

function passwordValidator(control: FormControl): {[s: string]: boolean} {
  if(!control.value?.match("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&%^$?=])[a-zA-Z0-9@#&%^$?=]{8,32}$")) {
    return {invalid: true};
  } else {
    return {invalid: false};
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  formGroup: FormGroup;
  username: FormControl;
  password: FormControl;
  name: FormControl;
  surname: FormControl;
  age: FormControl;


  constructor(private fb: FormBuilder, private authService: AuthService) { 
    this.formGroup = this.fb.group({      
      'username': ['', Validators.compose([usernameValidator, Validators.required])],
      'password': ['', Validators.compose([passwordValidator, Validators.required])],
      'name': ['', Validators.compose([nameValidator, Validators.required])],
      'surname': ['', Validators.compose([nameValidator, Validators.required])],
      'age': ['', Validators.compose([Validators.min(1), Validators.max(100), Validators.required])],
    });
    this.username = this.formGroup.controls['username'] as FormControl;
    this.password = this.formGroup.controls['password'] as FormControl;
    this.name = this.formGroup.controls['name'] as FormControl;
    this.surname = this.formGroup.controls['surname'] as FormControl;
    this.age = this.formGroup.controls['age'] as FormControl;
  }

  register(value: any) {
    const user = new User();
    user.name = value.name;
    user.surname = value.surname;
    user.age = value.age;
    user.username = value.username;
    user.password = value.password;
    
    this.authService.register(user);
    this.formGroup.reset();
  }
}
