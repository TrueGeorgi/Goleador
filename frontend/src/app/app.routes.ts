import { Routes } from '@angular/router';
import { IndexComponent } from './pages/index/index.component';
import { AboutMeComponent } from './pages/about-me/about-me.component';
import { AboutTheGameComponent } from './pages/about-the-game/about-the-game.component';
import { ClubPageComponent } from './pages/club-page/club-page.component';
import { EternalRanklistComponent } from './pages/eternal-ranklist/eternal-ranklist.component';
import { LoginComponent } from './pages/login/login.component';
import { ManagerProfileComponent } from './pages/manager-profile/manager-profile.component';
import { MatchWindowComponent } from './pages/match-window/match-window.component';
import { PastResultsComponent } from './pages/past-results/past-results.component';
import { PlayerProfileComponent } from './pages/player-profile/player-profile.component';
import { RegisterComponent } from './pages/register/register.component';
import { TeamViewComponent } from './pages/team-view/team-view.component';
import { TrainingComponent } from './pages/training/training.component';
import { TransferWindowComponent } from './pages/transfer-window/transfer-window.component';

export const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'transfer-window', component: TransferWindowComponent},
    {path: 'training', component: TrainingComponent},
    {path: 'team-view', component: TeamViewComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'player-profile', component: PlayerProfileComponent},
    {path: 'past-results', component: PastResultsComponent},
    {path: 'match-window', component: MatchWindowComponent},
    {path: 'manager-profile', component: ManagerProfileComponent},
    {path: 'login', component: LoginComponent},
    {path: 'eternal-ranklist', component: EternalRanklistComponent},
    {path: 'club-page', component: ClubPageComponent},
    {path: 'about-the-game', component: AboutTheGameComponent},
    {path: 'about-me', component: AboutMeComponent},
    {path: '**', component: IndexComponent},
];
