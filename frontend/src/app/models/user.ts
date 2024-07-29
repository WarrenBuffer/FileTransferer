import { UserFile } from "./user-file";

export class User {
    name!: string;
    surname!: string;
    age!: number;
    username!: string;
    password!: string;
    files!: UserFile[]
}
