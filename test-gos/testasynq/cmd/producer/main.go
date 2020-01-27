package main

import (
	"fmt"
	"time"

	"github.com/hibiken/asynq"
)

func main() {
	r := &asynq.RedisClientOpt{
		Addr: "localhost:6379",
	}

	client := asynq.NewClient(r)

	t1 := asynq.NewTask("send_welcome_email", map[string]interface{}{"user_id": 42})
	t2 := asynq.NewTask("send_reminder_email", map[string]interface{}{"user_id": 42})

	err := client.Schedule(t1, time.Now())

	// Process 24 hrs later
	err = client.Schedule(t2, time.Now().Add(24*time.Hour))

	if err != nil {
		fmt.Println(err)
	}
}
