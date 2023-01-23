# Scooter Rental App
## Run app locally in docker compose
1. Generate personal access token with repo and read packages access [instruction](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
2. login to GitHub container registry [instruction](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry)
3. Available docker runs

| What                              | Where         | Command                                               |
|-----------------------------------| ------------- |-------------------------------------------------------|
| Run mysql only                    | docker/mysql/ | `docker compose up -d`                                |
| Run app on main                   | docker/       | `docker compose pull && docker compose up -d`         |
| Build jar                         | /             | `mvn clean package -P dev`                            |
| Build Docker Image (Jar required) | /             | `docker build -t scooter-backend .` |
| Run Docker from image             | /             | `docker run -p 8080:8080  scooter-backend`            |

To delete GitHub image from docker compose:
- in folder /docker
- `docker-compose down -v`