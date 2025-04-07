import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-chat-search',
  templateUrl: './chat-search.component.html',
  styleUrls: ['./chat-search.component.css']
})
export class ChatSearchComponent {
  isOpen = false;
  query = '';
  messages: any[] = [];

  constructor(private http: HttpClient) {}

  toggleChat() {
    this.isOpen = !this.isOpen;
  }

  sendMessage() {
    if (!this.query) return;

    // Ajout du message de l'utilisateur
    this.messages.push({ role: 'user', text: this.query });

    // Simulation de "taper" du bot
    const typingMsg = { role: 'bot', typing: true };
    this.messages.push(typingMsg);

    const q = this.query;
    this.query = ''; // Clear input

    // Délai pour simuler la réponse du bot
    setTimeout(() => {
      this.http.get<any[]>(`http://localhost:8089/api/search?query=${q}`).subscribe(res => {
        // Supprimer le message de "taper" et ajouter la réponse du bot
        const index = this.messages.indexOf(typingMsg);
        if (index !== -1) this.messages.splice(index, 1);

        this.messages.push({ role: 'bot', produits: res });
      });
    }, 1000); // temps pour simuler le "taper"
  }
}
