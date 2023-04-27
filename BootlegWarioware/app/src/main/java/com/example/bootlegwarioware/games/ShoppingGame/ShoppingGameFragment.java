package com.example.bootlegwarioware.games.ShoppingGame;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bootlegwarioware.DemoGameFragmentArgs;
import com.example.bootlegwarioware.DemoGameFragmentDirections;
import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentShoppingGameBinding;
import com.example.bootlegwarioware.games.OrderGame.OrderGameViewModel;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class ShoppingGameFragment extends Fragment implements View.OnDragListener, View.OnLongClickListener{

    private ShoppingGameViewModel viewModel;
    private FragmentShoppingGameBinding binding;
    private String dropOffLocation;

    int difficulty;
    int speed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ShoppingGameViewModel.class);
        binding = FragmentShoppingGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        difficulty = ShoppingGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
        speed = ShoppingGameFragmentArgs.fromBundle(requireArguments()).getSpeed();
//        difficulty = 3;

        // Start animation for animated hands
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.hands.getBackground();
        progressAnimation.start();

        // Get the strings that are outputed in this model
        implementListeners(difficulty);
//        for (String str: viewModel.masterCandyList){
//            System.out.println(str);
//        }

        // Choose which candy of choice randomly, then fit the candyOfChoice image with said candy
        viewModel.candyOfChoice = viewModel.masterCandyList[new Random().nextInt(viewModel.masterCandyList.length)];

        // Set the candyOfChoice imageView with appropriate drawable
        binding.candyOfChoice.setImageResource(getDrawableID(viewModel.candyOfChoice));
//        System.out.println("candyOfChoice: " + viewModel.candyOfChoice);

        // Timer Bar
        binding.minigameTimerBar.setProgress(100);
        CountDownTimer countDown = new CountDownTimer(viewModel.milliSecCounter, viewModel.milliSecInterval) {
            @Override
            public void onTick(long l) {
                viewModel.i++;
                binding.minigameTimerBar.setProgress(100 - (viewModel.i*100/(viewModel.milliSecCounter/viewModel.milliSecInterval)));
            }

            @Override
            public void onFinish() {
                viewModel.i++;
                binding.minigameTimerBar.setProgress(0);
                NavDirections action = ShoppingGameFragmentDirections.actionShoppingGameFragmentToGameResultsFragment(viewModel.isGameCompleted);
                Navigation.findNavController(view).navigate(action);
            }
        };

        countDown.start();

        return view;
    }

    //Implement long click and drag listeners based on difficutly
    private void implementListeners(int difficulty) {

        // Set the top row tray and candy with their listeners
        binding.candyTopLeft.setTag("CANDY_TOP_LEFT");
        binding.candyTopLeft.setOnLongClickListener(this);
        binding.candyTopCenter.setTag("CANDY_TOP_CENTER");
        binding.candyTopCenter.setOnLongClickListener(this);
        binding.candyTopRight.setTag("CANDY_TOP_RIGHT");
        binding.candyTopRight.setOnLongClickListener(this);
        binding.topRowTray.setOnDragListener(this);

        switch (difficulty){
            // If difficutly is 3/hard, set the lower row tray and candy with their listeners
            case 3:
                binding.candyLowerLeft.setTag("CANDY_LOWER_LEFT");
                binding.candyLowerLeft.setOnLongClickListener(this);
                binding.candyLowerCenter.setTag("CANDY_LOWER_CENTER");
                binding.candyLowerCenter.setOnLongClickListener(this);
                binding.candyLowerRight.setTag("CANDY_LOWER_RIGHT");
                binding.candyLowerRight.setOnLongClickListener(this);
                binding.lowerRowTray.setOnDragListener(this);
                viewModel.masterCandyList = viewModel.stringArrayCombine(viewModel.masterCandyList, viewModel.additionalHardDifficultyList);

            // If difficutly is 2/medium or 3/hard, set the mid row tray and candy with their listeners
            case 2:
                binding.candyMidLeft.setTag("CANDY_MID_LEFT");
                binding.candyMidLeft.setOnLongClickListener(this);
                binding.candyMidCenter.setTag("CANDY_MID_CENTER");
                binding.candyMidCenter.setOnLongClickListener(this);
                binding.candyMidRight.setTag("CANDY_MID_RIGHT");
                binding.candyMidRight.setOnLongClickListener(this);
                binding.midRowTray.setOnDragListener(this);
                viewModel.masterCandyList = viewModel.stringArrayCombine(viewModel.masterCandyList, viewModel.additionalMediumDifficultyList);

                // If difficutly is 2/medium, set the low tray as GONE
                if(difficulty == 2){
                    binding.lowerRowTray.setVisibility(View.GONE);
                }
                break;

            // If difficulty is 1/easy, set the low and mid tray as GONE
            case 1:
            default:
                binding.midRowTray.setVisibility(View.GONE);
                binding.lowerRowTray.setVisibility(View.GONE);
                break;
        }

        binding.hands.setOnDragListener(this);
    }

    // Given the draggable objects name, return the drawable ID
    private int getDrawableID(String candyName){
        switch (candyName){
            case "CANDY_TOP_LEFT":
                return R.drawable.candy_1;
            case "CANDY_TOP_CENTER":
                return R.drawable.candy_2;
            case "CANDY_TOP_RIGHT":
                return R.drawable.candy_3;
            case "CANDY_MID_LEFT":
                return R.drawable.candy_4;
            case "CANDY_MID_CENTER":
                return R.drawable.candy_5;
            case "CANDY_MID_RIGHT":
                return R.drawable.candy_6;
            case "CANDY_LOWER_LEFT":
                return R.drawable.candy_7;
            case "CANDY_LOWER_CENTER":
                return R.drawable.candy_8;
            case "CANDY_LOWER_RIGHT":
                return R.drawable.candy_9;
            default:
                return R.drawable.fly;
        }
    };

    // Disable all candies and switch up backgrounds
    boolean isCorrectCandy(String guess, String answer, int difficulty){

        // Disable all known candies
        switch (difficulty) {
            case 3:
                binding.candyLowerLeft.setEnabled(false);
                binding.candyLowerCenter.setEnabled(false);
                binding.candyLowerRight.setEnabled(false);
            case 2:
                binding.candyMidLeft.setEnabled(false);
                binding.candyMidCenter.setEnabled(false);
                binding.candyMidRight.setEnabled(false);
            case 1:
            default:
                binding.candyTopLeft.setEnabled(false);
                binding.candyTopCenter.setEnabled(false);
                binding.candyTopRight.setEnabled(false);
                break;
        }

        boolean results = Objects.equals(guess, answer);
        binding.candyOfChoice.setVisibility(View.INVISIBLE);

        // Change the background based on results
        if (results){
            binding.microgameShoppingBackground.setBackgroundResource(R.drawable.microgame_shopping_win);
            final MediaPlayer correct = MediaPlayer.create(getActivity(), R.raw.correct_sound_effect);
            correct.start();
        } else {
            binding.microgameShoppingBackground.setBackgroundResource(R.drawable.microgame_shopping_lose);
            final MediaPlayer incorrect = MediaPlayer.create(getActivity(), R.raw.incorrect_sound_effect);
            incorrect.start();
        }
//        System.out.println("GAME RESULTS: " + Objects.equals(guess, answer));

        return results;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        // Defines a variable to store the action type for the incoming event
        int action = dragEvent.getAction();

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.
                    //view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                    // Invalidate the view to force a redraw in the new tint
                    // view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                // Applies a MAGENTA or any color tint to the View,
                // Return true; the return value is ignored.

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
//                System.out.println("Dragged Data: " + dragData);

                // Invalidates the view to force a redraw
                view.invalidate();

                //get dragged view
                View v = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v); //remove the dragged view
                LinearLayout container = (LinearLayout) view; //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v); //Add the dragged view

                String fullName = getResources().getResourceName(view.getId());
                dropOffLocation = fullName.substring(fullName.lastIndexOf("/") + 1);
//                System.out.println("DROPPED LOCATION: " + dropOffLocation);

                // If the dropped location is "hands", then check if its the correct candy
                if(Objects.equals(dropOffLocation, "hands")){
                    viewModel.isGameCompleted = isCorrectCandy(dragData, viewModel.candyOfChoice, difficulty);
                }

                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                // Invalidates the view to force a redraw
                view.invalidate();

//                view.post(new Runnable() {
//                    public void run() {
//                        dragView.setVisibility(View.VISIBLE);
//                    }
//                });

                // invoke getResult(), and displays what happened.
                if (dragEvent.getResult()) {
//                    System.out.println("The drop was handled.");

                } else {
//                    System.out.println("The drop didn't work.");
                }



                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
//                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


    @Override
    public boolean onLongClick(View view) {

        // Create a new ClipData.Item from the tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data //data to be dragged
                , shadowBuilder //drag shadow
                , view //local data about the drag and drop operation
                , 0 //flags (not currently used, set to 0)
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
//        view.setVisibility(View.INVISIBLE);

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}