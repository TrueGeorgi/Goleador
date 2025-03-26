import { GameData } from "./GameData";

interface ClubPageData {
    goalscorers: Goalscorer[];
    lastResult:  GameData;
    teamLogo: string;
    managerLogo: string;
    managerName: string;
    gamesPlayed: number;
    currentPoints: number;
    eternalPosition: number;
    finances: number;
}