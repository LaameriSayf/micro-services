import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CategorieComponent } from './categorie/categorie.component';
import { ProduitComponent } from './produit/produit.component';
import { LoginComponent } from './login/login.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { KeycloakService } from './keycloak/keycloak.service';
import { ChatSearchComponent } from './chat-search/chat-search.component';
import { FrontproduitComponent } from './frontproduit/frontproduit.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthInterceptor } from './services/auth-interceptor.service';


export function kcFactory(kcService: KeycloakService) {
  return () => kcService.init();
  
  }

@NgModule({
  declarations: [
    AppComponent,
    CategorieComponent,
    ProduitComponent,
    LoginComponent,
    SidebarComponent,
    ChatSearchComponent,
    FrontproduitComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,FormsModule
  ],
  providers: [
    

    {
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      useFactory: kcFactory,
      multi: true
      
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
