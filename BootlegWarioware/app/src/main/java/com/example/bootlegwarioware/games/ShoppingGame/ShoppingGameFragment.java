package com.example.bootlegwarioware.games.ShoppingGame;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
//        difficulty = 1;
        speed = ShoppingGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        // Get the
        implementListeners(difficulty);
        for (String str: viewModel.masterCandyList){
            System.out.println(str);
        }

        // Choose which candy is the answer randomly
        viewModel.candyOfChoice = viewModel.masterCandyList[new Random().nextInt(viewModel.masterCandyList.length)];
        System.out.println("candyOfChoice: " + viewModel.candyOfChoice);

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

    //Implement long click and drag listener
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
            case 3:
                binding.candyLowerLeft.setTag("CANDY_LOWER_LEFT");
                binding.candyLowerLeft.setOnLongClickListener(this);
                binding.candyLowerCenter.setTag("CANDY_LOWER_CENTER");
                binding.candyLowerCenter.setOnLongClickListener(this);
                binding.candyLowerRight.setTag("CANDY_LOWER_RIGHT");
                binding.candyLowerRight.setOnLongClickListener(this);
                binding.lowerRowTray.setOnDragListener(this);
                viewModel.masterCandyList = viewModel.stringArrayCombine(viewModel.masterCandyList, viewModel.additionalHardDifficultyList);

            case 2:
                binding.candyMidLeft.setTag("CANDY_MID_LEFT");
                binding.candyMidLeft.setOnLongClickListener(this);
                binding.candyMidCenter.setTag("CANDY_MID_CENTER");
                binding.candyMidCenter.setOnLongClickListener(this);
                binding.candyMidRight.setTag("CANDY_MID_RIGHT");
                binding.candyMidRight.setOnLongClickListener(this);
                binding.midRowTray.setOnDragListener(this);
                viewModel.masterCandyList = viewModel.stringArrayCombine(viewModel.masterCandyList, viewModel.additionalMediumDifficultyList);

                if(difficulty == 2){
                    binding.lowerRowTray.setVisibility(View.GONE);
                }
                break;

            case 1:
            default:
                binding.midRowTray.setVisibility(View.GONE);
                binding.lowerRowTray.setVisibility(View.GONE);
                break;
        }

        binding.hands.setOnDragListener(this);
    }

    boolean isCorrectCandy(String guess, String answer, int difficulty){
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

        System.out.println("GAME RESULTS: " + Objects.equals(guess, answer));
        return Objects.equals(guess, answer);
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

                view.getBackground().setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
//                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                System.out.println("Dragged Data: " + dragData);

                // Turns off any color tints
                view.getBackground().clearColorFilter();

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
                System.out.println("DROPPED LOCATION: " + dropOffLocation);

                // If the dropped location is "hands", then check if its the correct candy
                if(Objects.equals(dropOffLocation, "hands")){
                    viewModel.isGameCompleted = isCorrectCandy(dragData, viewModel.candyOfChoice, difficulty);
                }

                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

//                view.post(new Runnable() {
//                    public void run() {
//                        dragView.setVisibility(View.VISIBLE);
//                    }
//                });

                // invoke getResult(), and displays what happened.
                if (dragEvent.getResult()) {
//                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                    System.out.println("The drop was handled.");

                } else {
//                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    System.out.println("The drop didn't work.");
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