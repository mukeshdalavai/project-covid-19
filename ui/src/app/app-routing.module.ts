import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {FaqComponent} from './faq/faq.component';
import {ApiComponent} from './api/api.component';


const routes: Routes = [
  {path : '',component :HomeComponent},
  {path : 'faq',component :FaqComponent},
  {path : 'api',component :ApiComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
