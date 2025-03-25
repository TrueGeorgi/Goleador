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
import { AuthGuard } from './guards/auth.guard';
import { GuestGuard } from './guards/guest.guard';
import { EditProfileComponent } from './pages/edit-profile/edit-profile.component';
import { EditClubComponent } from './pages/edit-club/edit-club.component';

export const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'training', component: TrainingComponent, canActivate: [AuthGuard]},
    {path: 'team-view', component: TeamViewComponent, canActivate: [AuthGuard]},
    {path: 'register', component: RegisterComponent, canActivate: [GuestGuard]},
    {path: 'player-profile', component: PlayerProfileComponent, canActivate: [AuthGuard]},
    {path: 'past-results', component: PastResultsComponent, canActivate: [AuthGuard]},
    {path: 'match-window', component: MatchWindowComponent, canActivate: [AuthGuard]},
    {path: 'manager-profile', component: ManagerProfileComponent, canActivate: [AuthGuard]},
    {path: 'login', component: LoginComponent, canActivate: [GuestGuard]},
    {path: 'eternal-ranklist', component: EternalRanklistComponent, canActivate: [AuthGuard]},
    {path: 'club-page', component: ClubPageComponent, canActivate: [AuthGuard]},
    {path: 'about-the-game', component: AboutTheGameComponent},
    {path: 'about-me', component: AboutMeComponent},
    {path: 'edit-profile', component: EditProfileComponent, canActivate: [AuthGuard]},
    {path: 'edit-club', component: EditClubComponent, canActivate: [AuthGuard]},
    {path: '**', component: AboutMeComponent, canActivate: [GuestGuard]},
];
